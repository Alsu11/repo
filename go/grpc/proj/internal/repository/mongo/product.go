package mongo

import (
	"Hw2/internal/core"
	"context"
	"fmt"
	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/bson/primitive"
	"go.mongodb.org/mongo-driver/mongo"
	"time"
)

type ProductRepository struct {
	collection *mongo.Collection
}

func NewProductRepository(collection *mongo.Collection) *ProductRepository {
	return &ProductRepository{collection: collection}
}

func (repository *ProductRepository) GetById(ctx context.Context, id string) (*core.Product, error) {
	ctxTimeout, cancel := context.WithTimeout(ctx, time.Second*5)

	defer cancel()

	productChannel := make(chan *core.Product, 0)
	var err error

	go func() {
		err = repository.retrieveProduct(ctx, id, productChannel)
	}()

	if err != nil {
		return nil, err
	}

	var product *core.Product

	select {
	case <-ctxTimeout.Done():
		fmt.Println("Processing timeout in Mongo")
		break
	case product = <-productChannel:
		fmt.Println("Finished processing in Mongo")
	}

	return product, nil
}

func (repository *ProductRepository) retrieveProduct(ctx context.Context, id string, channel chan<- *core.Product) (err error) {
	objectId, err := primitive.ObjectIDFromHex(id)
	product := &core.Product{}
	err = repository.collection.FindOne(ctx, bson.M{"_id": objectId}).Decode(product)

	if err != nil {
		return err
	}

	channel <- product
	return nil
}

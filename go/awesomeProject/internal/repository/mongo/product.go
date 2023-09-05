package mongo

import (
	"awesomeProject/internal/model"
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

func (repository *ProductRepository) GetAll(ctx context.Context) ([]*model.Product, error) {
	ctxTimeout, cancel := context.WithTimeout(ctx, time.Second*5)

	defer cancel()

	productChannel := make(chan []*model.Product, 0)
	var err error

	go func() {
		err = repository.retrieveAll(ctx, productChannel)
	}()

	if err != nil {
		return nil, err
	}

	var products []*model.Product

	select {
	case <-ctxTimeout.Done():
		fmt.Println("Processing timeout in Mongo")
		break
	case products = <-productChannel:
		fmt.Println("Finished processing in Mongo")
	}

	return products, nil
}

func (repository *ProductRepository) retrieveAll(ctx context.Context, channel chan<- []*model.Product) (err error) {
	var products []*model.Product

	cursor, err := repository.collection.Find(ctx, bson.D{})

	err = cursor.All(ctx, &products)

	if err != nil {
		return err
	}

	channel <- products
	return nil
}

func (repository *ProductRepository) GetById(ctx context.Context, id string) (*model.Product, error) {
	ctxTimeout, cancel := context.WithTimeout(ctx, time.Second*5)

	defer cancel()

	productChannel := make(chan *model.Product, 0)
	var err error

	go func() {
		err = repository.retrieveUser(ctx, id, productChannel)
	}()

	if err != nil {
		return nil, err
	}

	var product *model.Product

	select {
	case <-ctxTimeout.Done():
		fmt.Println("Processing timeout in Mongo")
		break
	case product = <-productChannel:
		fmt.Println("Finished processing in Mongo")
	}

	return product, nil
}

func (repository *ProductRepository) retrieveUser(ctx context.Context, id string, channel chan<- *model.Product) (err error) {
	objectId, err := primitive.ObjectIDFromHex(id)
	product := &model.Product{}
	err = repository.collection.FindOne(ctx, bson.M{"_id": objectId}).Decode(product)

	if err != nil {
		return err
	}

	channel <- product
	return nil
}

func (repository *ProductRepository) Save(ctx context.Context, product *model.Product) (*model.Product, error) {

	result, err := repository.collection.InsertOne(ctx, product)

	if err != nil {
		return nil, err
	}
	product.Id = result.InsertedID.(primitive.ObjectID)

	return product, nil
}

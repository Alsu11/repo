package mongo

import (
	"4.MongoWebProject/internal/model"
	"context"
	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/bson/primitive"
	"go.mongodb.org/mongo-driver/mongo"
)

type ProductRepository struct {
	collection *mongo.Collection
}

func NewProductRepository(collection *mongo.Collection) *ProductRepository {
	return &ProductRepository{collection: collection}
}

func (repository *ProductRepository) GetAll(ctx context.Context) ([]*model.Product, error) {
	products := make([]*model.Product, 0)

	cursor, err := repository.collection.Find(ctx, bson.D{})

	if err != nil {
		return nil, err
	}

	err = cursor.All(ctx, &products)
	if err != nil {
		return nil, err
	}

	return products, nil
}

func (repository *ProductRepository) GetById(ctx context.Context, id string) (*model.Product, error) {
	objectId, err := primitive.ObjectIDFromHex(id)

	if err != nil {
		return nil, err
	}

	product := &model.Product{}
	err = repository.collection.FindOne(ctx, bson.M{"_id": objectId}).Decode(product)

	if err != nil {
		return nil, err
	}

	return product, nil
}

func (repository *ProductRepository) Save(ctx context.Context, product *model.Product) (*model.Product, error) {

	result, err := repository.collection.InsertOne(ctx, product)

	if err != nil {
		return nil, err
	}
	product.Id = result.InsertedID.(primitive.ObjectID)

	return product, nil
}

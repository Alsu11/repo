package config

import (
	"context"
	"github.com/spf13/viper"
	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
)

type MongoConfig struct {
	URI  string
	Name string
}

func SetupMongoDatabase(ctx context.Context, cancel context.CancelFunc) (*mongo.Database, error) {
	config := &MongoConfig{}

	err := viper.UnmarshalKey("mongo.database", config)

	if err != nil {
		return nil, err
	}

	client, err := mongo.NewClient(options.Client().ApplyURI(config.URI))

	if err != nil {
		return nil, err
	}

	err = client.Connect(ctx)

	defer cancel()
	return client.Database(config.Name), nil
}

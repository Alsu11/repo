package core

import "go.mongodb.org/mongo-driver/bson/primitive"

type Product struct {
	Id       primitive.ObjectID `bson:"_id,omitempty" json:"id"`
	Name     string             `bson:"name,omitempty" json:"name"`
	Category string             `bson:"category,omitempty" json:"category"`
	Price    float64            `bson:"price,omitempty" json:"price"`
}

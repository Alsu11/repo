package model

import "go.mongodb.org/mongo-driver/bson/primitive"

type Product struct {
	Id    primitive.ObjectID `bson:"_id,omitempty" json:"id"`
	Title string             `bson:"title,omitempty" json:"title"`
	Cost  int                `bson:"cost,omitempty" json:"cost"`
}

package main

import (
	"awesomeProject/internal/config"
	"awesomeProject/internal/repository/mongo"
	"awesomeProject/internal/service"
	"awesomeProject/internal/transport/rest/handler"
	"context"
	"github.com/gofiber/fiber/v2"
	"github.com/spf13/viper"
	"log"
	"time"
)

// @title 4.Mongo Web Project
// @version 2.0
// @description This is a sample server
// @termsOfService http://swagger.io/terms/
// @host localhost:8080
// @BasePath /
// @schemes http
func main() {
	err := SetupViper()
	if err != nil {
		log.Fatal(err.Error())
	}

	app := fiber.New()
	config.SetupSwagger(app)

	context, cancel := context.WithTimeout(context.Background(), 10*time.Second)

	mongoDB, err := config.SetupMongoDatabase(context, cancel)
	if err != nil {
		log.Fatal(err.Error())
	}

	productRepository := mongo.NewProductRepository(mongoDB.Collection("products"))
	productService := service.NewProductService(productRepository)
	productHandler := handler.NewProductHandler(productService)
	productHandler.InitRoutes(app)

	port := viper.GetString("http.port")
	if err := app.Listen(":" + port); err != nil {
		log.Fatal(err)
	}
}

func SetupViper() error {
	viper.AddConfigPath("configs")
	viper.SetConfigName("config")
	if err := viper.ReadInConfig(); err != nil {
		return err
	}
	return nil
}

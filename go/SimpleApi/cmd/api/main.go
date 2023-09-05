package main

import (
	_ "04._SimpleApi/docs"
	"04._SimpleApi/internal/repository/memory"
	"04._SimpleApi/internal/service"
	"04._SimpleApi/internal/transport/rest/handler"
	"github.com/gofiber/fiber/v2"
	"github.com/gofiber/swagger"
	"github.com/spf13/viper"
	"log"
)

// @title Fiber Swagger Example API
// @version 2.0
// @description Lets get products!!!)
// @termsOfService http://swagger.io/terms/
// @host localhost:3000
// @BasePath /
// @schemes http
func main() {
	viper.SetConfigFile(".env")

	if err := viper.ReadInConfig(); err != nil {
		log.Panic(err)
	}

	port := viper.Get("PORT").(string)

	productRepository := memory.NewProductRepository()
	productService := service.NewProductService(productRepository)
	productHandler := handler.NewProductHandler(productService)

	app := fiber.New()

	app.Get("/swagger/*", swagger.HandlerDefault)

	productHandler.InitRoutes(app)

	if err := app.Listen(":" + port); err != nil {
		log.Panic(err)
	}
}

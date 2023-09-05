package handler

import (
	"04._SimpleApi/internal/core"
	"github.com/gofiber/fiber/v2"
	"net/http"
	"strconv"
)

type ProductService interface {
	GetAll() []*core.Product
	GetById(id int) *core.Product
}

type ProductHandler struct {
	service ProductService
}

func NewProductHandler(service ProductService) *ProductHandler {
	return &ProductHandler{service: service}
}

func (handler *ProductHandler) InitRoutes(app *fiber.App) {
	app.Get("/products", handler.GetAll)
	app.Get("/products/:productId", handler.GetById)
}

// GetAll
// @Summary Get all products
// @Tags products
// @Description Returns the list of the products
// @Produce json
// @Status 200
// @Router /products [get]
func (handler *ProductHandler) GetAll(ctx *fiber.Ctx) error {
	products := handler.service.GetAll()

	return ctx.Status(200).JSON(
		fiber.Map{
			"products": products,
		})
}

// GetById
// @Summary Get product by id
// @Tags products
// @Description Returns the product by id
// @Produce json
// @Status 200
// @Param productId path string true "Product ID"
// @Router /products/{productId} [get]
func (handler *ProductHandler) GetById(ctx *fiber.Ctx) error {

	productId, err := strconv.Atoi(ctx.Params("productId"))

	if err != nil {
		return ctx.Status(http.StatusBadRequest).JSON(
			fiber.Map{
				"error": "Incorrect id format",
			})
	}

	product := handler.service.GetById(productId)

	return ctx.Status(200).JSON(
		fiber.Map{
			"product": product,
		})
}

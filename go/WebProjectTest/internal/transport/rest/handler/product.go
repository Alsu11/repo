package handler

import (
	"5.WebProjectTest/internal/model"
	"context"
	"github.com/gofiber/fiber/v2"
	"net/http"
)

type ProductService interface {
	GetAll(ctx context.Context) ([]*model.Product, error)
	GetById(ctx context.Context, id string) (*model.Product, error)
	Save(ctx context.Context, product *model.Product) (*model.Product, error)
}

type ProductHandler struct {
	service ProductService
}

func NewProductHandler(service ProductService) *ProductHandler {
	return &ProductHandler{service: service}
}

func (handler *ProductHandler) InitRoutes(app *fiber.App) {
	app.Get("/products", handler.getAll)
	app.Get("/products/:Id", handler.getById)
	app.Post("/products", handler.save)
}

// GetAll
// @Summary Get all products
// @Tags products
// @Description Returns the list of the products
// @Produce json
// @Status 200
// @Router /products [get]
func (handler *ProductHandler) getAll(ctx *fiber.Ctx) error {
	products, err := handler.service.GetAll(ctx.UserContext())

	if err != nil {
		return ctx.Status(http.StatusInternalServerError).JSON(
			fiber.Map{
				"error": err.Error(),
			})
	}

	return ctx.Status(http.StatusOK).JSON(
		fiber.Map{
			"products": products,
		})
}

// GetById
// @Summary Get product by id
// @Tags product by id
// @Description Returns the product by id
// @Produce json
// @Param id path string true "Product id"
// @Status 200
// @Router /products/{id} [get]
func (handler *ProductHandler) getById(ctx *fiber.Ctx) error {
	id := ctx.Params("Id")

	product, err := handler.service.GetById(ctx.UserContext(), id)

	if err != nil {
		return ctx.Status(http.StatusInternalServerError).JSON(
			fiber.Map{
				"error": err.Error(),
			})
	}

	return ctx.Status(http.StatusOK).JSON(
		fiber.Map{
			"product": product,
		})
}

// Save
// @Summary Save product
// @Tags product
// @Description Returns the product
// @Produce json
// @Reduce json
// @Param Product body request.ProductForm true "shop product"
// @Status 200
// @Router /products [post]
func (handler *ProductHandler) save(ctx *fiber.Ctx) error {

	product := &model.Product{}

	if err := ctx.BodyParser(product); err != nil {
		return ctx.Status(http.StatusBadRequest).JSON(
			fiber.Map{
				"error": err.Error(),
			})
	}

	savedProduct, err := handler.service.Save(ctx.UserContext(), product)

	if err != nil {
		return ctx.Status(http.StatusInternalServerError).JSON(
			fiber.Map{
				"error": err,
			})
	}

	return ctx.Status(http.StatusOK).JSON(
		fiber.Map{
			"product": savedProduct,
		})
}

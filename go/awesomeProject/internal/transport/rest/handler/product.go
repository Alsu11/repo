package handler

import (
	"awesomeProject/internal/model"
	"context"
	"fmt"
	"github.com/gofiber/fiber/v2"
	"net/http"
	"time"
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
	ctxTimeout, cancel := context.WithTimeout(ctx.UserContext(), time.Second*2)
	defer cancel()

	channel := make(chan []*model.Product, 0)

	var err error
	var products []*model.Product

	go func(channel chan<- []*model.Product) {
		products, err = handler.service.GetAll(ctxTimeout)

		channel <- products

	}(channel)

	if err != nil {
		return err
	}

	select {
	case <-ctxTimeout.Done():
		fmt.Println("Processing timeout in Handler")
		break
	case products = <-channel:
		fmt.Println("Finished processing in Handler")
	}

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
	ctxTimeout, cancel := context.WithTimeout(ctx.UserContext(), time.Second*2)
	defer cancel()

	channel := make(chan *model.Product, 0)

	var err error
	var product *model.Product

	go func(channel chan<- *model.Product) {
		product, err = handler.service.GetById(ctxTimeout, ctx.Params("Id"))

		channel <- product

	}(channel)

	if err != nil {
		return err
	}

	select {
	case <-ctxTimeout.Done():
		fmt.Println("Processing timeout in Handler")
		break
	case product = <-channel:
		fmt.Println("Finished processing in Handler")
	}

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

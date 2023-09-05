package handler

import (
	"5.WebProjectTest/internal/model"
	"bytes"
	"encoding/json"
	"github.com/gofiber/fiber/v2"
	"github.com/stretchr/testify/assert"
	"go.mongodb.org/mongo-driver/bson/primitive"
	"net/http"
	"net/http/httptest"
	"testing"
)

func TestProductHandler_getById(t *testing.T) {

	tests := []struct {
		description  string
		route        string
		expectedCode int
	}{
		{
			description:  "get product by id, HTTP status 200",
			route:        "/product/635800cae67382a16da6f5db",
			expectedCode: 200,
		},
		{
			description:  "not found, HTTP status 404",
			route:        "/not-found",
			expectedCode: 404,
		},
	}

	app := fiber.New()

	app.Get("/product/:Id", func(c *fiber.Ctx) error {
		return c.JSON(fiber.Map{
			"product": model.Product{
				Title: "Banana",
				Cost:  70,
			},
		})
	})

	for _, tt := range tests {
		req := httptest.NewRequest(http.MethodGet, tt.route, nil)
		resp, _ := app.Test(req, 5)
		assert.Equalf(t, tt.expectedCode, resp.StatusCode, tt.description)
	}
}

func TestProductHandler_getAll(t *testing.T) {

	tests := []struct {
		description  string
		route        string
		expectedCode int
	}{
		{
			description:  "get product by id, HTTP status 200",
			route:        "/products",
			expectedCode: 200,
		},
		{
			description:  "not found, HTTP status 404",
			route:        "/not-found",
			expectedCode: 404,
		},
	}

	app := fiber.New()

	app.Get("/products", func(c *fiber.Ctx) error {
		return c.JSON(fiber.Map{
			"product": []model.Product{
				{
					Id:    primitive.ObjectID{},
					Title: "Banana",
					Cost:  70,
				},
				{
					Id:    primitive.ObjectID{},
					Title: "Apple",
					Cost:  60,
				},
			}})
	})

	for _, tt := range tests {
		req := httptest.NewRequest("GET", tt.route, nil)
		resp, _ := app.Test(req, 1)
		assert.Equalf(t, tt.expectedCode, resp.StatusCode, tt.description)
	}
}

func TestProductHandler_save(t *testing.T) {

	tests := []struct {
		description  string
		route        string
		expectedCode int
		body         model.Product
	}{
		{
			description:  "save product, HTTP status 200",
			route:        "/products",
			expectedCode: 200,
			body: model.Product{
				Title: "Banana",
				Cost:  70,
			},
		},
		{
			description:  "not found, HTTP status 404",
			route:        "/not-found",
			expectedCode: 404,
			body: model.Product{
				Title: "Banana",
				Cost:  70,
			},
		},
	}

	app := fiber.New()

	app.Post("/products", func(ctx *fiber.Ctx) error {

		product := &model.Product{}

		if err := ctx.BodyParser(product); err != nil {
			return ctx.Status(http.StatusBadRequest).JSON(
				fiber.Map{
					"error": err.Error(),
				})
		}

		return ctx.JSON(fiber.Map{
			"product": product,
		})
	})

	for _, tt := range tests {

		product, _ := json.Marshal(map[string]interface{}{
			"Title": tt.body.Title,
			"Cost":  tt.body.Cost,
		})

		req := httptest.NewRequest(http.MethodPost, tt.route, bytes.NewReader(product))
		req.Header.Add("Content-Type", "application/json")
		resp, _ := app.Test(req, 1)
		assert.Equalf(t, tt.expectedCode, resp.StatusCode, tt.description)
	}
}

package memory

import "04._SimpleApi/internal/core"

type ProductRepository struct {
	// список продуктов
	products []*core.Product
}

func NewProductRepository() *ProductRepository {
	repository := &ProductRepository{products: []*core.Product{}}

	product1 := &core.Product{
		ID:    1,
		Name:  "Milk",
		Maker: "Prostokvashino",
	}

	product2 := &core.Product{
		ID:    2,
		Name:  "Cheese",
		Maker: "Cheezzze",
	}

	repository.products = append(repository.products, product1)
	repository.products = append(repository.products, product2)

	return repository
}

func (repository *ProductRepository) GetAll() []*core.Product {
	return repository.products
}

func (repository *ProductRepository) GetById(id int) *core.Product {
	return repository.products[id-1]
}

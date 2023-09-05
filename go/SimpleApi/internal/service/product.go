package service

import "04._SimpleApi/internal/core"

type ProductRepository interface {
	GetAll() []*core.Product
	GetById(id int) *core.Product
}

type ProductService struct {
	productRepository ProductRepository
}

func NewProductService(repository ProductRepository) *ProductService {
	return &ProductService{productRepository: repository}
}

func (service *ProductService) GetAll() []*core.Product {
	return service.productRepository.GetAll()
}

func (service *ProductService) GetById(id int) *core.Product {
	return service.productRepository.GetById(id)
}

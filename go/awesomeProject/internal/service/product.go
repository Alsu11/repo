package service

import (
	"awesomeProject/internal/model"
	"context"
)

type ProductRepository interface {
	GetAll(ctx context.Context) ([]*model.Product, error)
	GetById(ctx context.Context, id string) (*model.Product, error)
	Save(ctx context.Context, product *model.Product) (*model.Product, error)
}

type ProductService struct {
	repository ProductRepository
}

func NewProductService(repository ProductRepository) *ProductService {
	return &ProductService{repository: repository}
}

func (service *ProductService) GetAll(ctx context.Context) ([]*model.Product, error) {
	return service.repository.GetAll(ctx)
}

func (service *ProductService) GetById(ctx context.Context, id string) (*model.Product, error) {
	return service.repository.GetById(ctx, id)
}

func (service *ProductService) Save(ctx context.Context, product *model.Product) (*model.Product, error) {
	return service.repository.Save(ctx, product)
}

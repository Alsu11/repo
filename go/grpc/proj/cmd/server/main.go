package main

import (
	"Hw2/internal/config"
	"Hw2/internal/repository/mongo"
	"Hw2/internal/server"
	pb "Hw2/proto"
	"context"
	"fmt"
	"github.com/spf13/viper"
	"google.golang.org/grpc"
	"log"
	"net"
	"time"
)

var (
	host = "localhost"
	port = "5000"
)

func main() {
	ctx, cancel := context.WithTimeout(context.Background(), 10*time.Second)

	err := setupViper()

	if err != nil {
		log.Fatalf("error reading yml file: %v", err)
	}

	addr := fmt.Sprintf("%s:%s", host, port)
	lis, err := net.Listen("tcp", addr)

	if err != nil {
		log.Fatalf("error starting tcp listener: %v", err)
	}

	mongoDataBase, err := config.SetupMongoDataBase(ctx, cancel)

	if err != nil {
		log.Fatalf("error starting mongo : %v", err)
	}

	productRepository := mongo.NewProductRepository(mongoDataBase.Collection("products"))

	userServer := server.NewProductServer(productRepository)

	grpcServer := grpc.NewServer()

	pb.RegisterProductServiceServer(grpcServer, userServer)

	log.Printf("gRPC started at %v\n", port)

	if err := grpcServer.Serve(lis); err != nil {
		log.Fatalf("error starting gRPC : %v", err)
	}

}

func setupViper() error {
	viper.AddConfigPath("configs")
	viper.SetConfigName("config")

	if err := viper.ReadInConfig(); err != nil {
		return err
	}

	return nil
}

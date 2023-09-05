package request

type ProductForm struct {
	Title string `json:"title"`
	Cost  int    `json:"cost"`
}

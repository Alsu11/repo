package main

import (
	"fmt"
	"github.com/tjarratt/babble"
	"os"
	"runtime/trace"
	"strings"
	"time"
)

const SliceSize = 100_000_000 //word count

type Counter struct {
	counts map[string]int
}

func (c *Counter) Count(word string) {
	c.counts[word]++
}

func (c *Counter) Print() {
	for word, count := range c.counts {
		fmt.Printf("%c -> %d \n", word, count)
	}
}

func GenerateWords(count int) (words []string) {
	babbler := babble.NewBabbler()
	babbler.Separator = " "
	babbler.Count = count
	words = strings.Split(babbler.Babble(), " ")
	return
}

func main() {

	f, err := os.Create("nogoroutine//trace.out")
	if err != nil {
		panic(err)
	}

	defer f.Close()

	err = trace.Start(f)
	if err != nil {
		panic(err)
	}
	defer trace.Stop()

	words := GenerateWords(SliceSize)

	c := &Counter{counts: make(map[string]int)}

	start := time.Now()
	for _, word := range words {
		c.Count(word)
	}
	fmt.Println(time.Now().Sub(start).Milliseconds())
	//c.Print()
}

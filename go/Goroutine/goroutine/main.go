package main

import (
	"fmt"
	"github.com/tjarratt/babble"
	"os"
	"runtime/trace"
	"strings"
	"sync"
	"sync/atomic"
	"time"
)

const SliceSize = 100_000_000
const GoroutinesCount = 10
const Range = SliceSize / GoroutinesCount

type Counter struct {
	counts map[string]*int32
	mutex  sync.RWMutex
}

func (c *Counter) Count(word string) {
	var count *int32

	if count = c.GetCount(word); count == nil {
		count = c.InitCount(word)
	}

	atomic.AddInt32(count, 1)

}

func (c *Counter) GetCount(word string) *int32 {
	defer c.mutex.RUnlock()
	c.mutex.RLock()
	return c.counts[word]
}

func (c *Counter) InitCount(word string) *int32 {
	count := c.GetCount(word)

	if count == nil {
		defer c.mutex.Unlock()
		c.mutex.Lock()
		value := int32(0)
		count = &value
		c.counts[word] = count
	}

	return count
}

func RunProcess(c *Counter, words []string, from, to int, wg *sync.WaitGroup) {
	defer wg.Done()

	for i := from; i <= to; i++ {
		c.Count(words[i])
	}
}

func (c *Counter) Print() {
	for char, count := range c.counts {
		fmt.Printf("%c -> %d \n", char, *count)
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

	f, err := os.Create("goroutine//trace.out")
	if err != nil {
		panic(err)
	}

	defer f.Close()

	err = trace.Start(f)
	if err != nil {
		panic(err)
	}
	defer trace.Stop()

	var wg sync.WaitGroup
	wg.Add(GoroutinesCount)

	words := GenerateWords(SliceSize)

	c := &Counter{counts: make(map[string]*int32)}

	start := time.Now()

	for i := 0; i < SliceSize; i += Range {
		go RunProcess(c, words, i, i+(Range-1), &wg)
	}

	wg.Wait()

	println(time.Now().Sub(start).Milliseconds())
	//c.Print()
}

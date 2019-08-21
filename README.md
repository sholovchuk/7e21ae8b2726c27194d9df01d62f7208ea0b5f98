# Finding most similar element

### Steps to calculate similarity of html element based on attributes values:
* Calculate *weight coefficient* of each attribute of a target element based on its lentgh:  
`attr1.value.length / ( attr1.value.length + attr2.value.length ... + attrN.value.length)`
* For each attribute with same key, calculate string similarity coefficient for values of two elements
* For each shared attribute multiply weight and *similarity coefficients*
* Summarize all result from previous step


### Calculation example

Attribute key | Element 1 attr  | Element 1 attr  | Length | Weight  | Similarity | Weighted Similarity
------------- | --------------- | --------------- | ------ | ------- | ---------- | -------------------
class         | btn btn-success | btn btn-default | 15     | 0.32    | 0.88       | 0.28
href          | #success        | #done           |  8     | 0.16    | 0.34       | 0.05
rel           | next            | last            |  4     | 0.08    | 0.50       | 0.04
title         | It's all ok     |                 | 11     | 0.22    | 0.00       | 0.00
onclick       | function1()     | function2()     | 11     | 0.22    | 0.98       | 0.21
**TOTAL:**    |                 |                 | 49     | 1.00    |            | **0.58**

In this example elemets *similarity coefficient* is **0.58**

So, all compared elements are stored in a sorted map in this implementation, with reverse order sorting,
where key is *similarity coefficient* and value is element itself. And top element in this sorted map is the most similar to
a target element.

## Run

`java -jar app.jar <required: abs path file1> <required: abs path file2> <optoinal: element id from file1>`  
  
output is just an xPath to the most similar element from file2

### Run on examples

```
$ java -jar app.jar sample-0-origin.html sample-1-evil-gemini.html
html > body > div > div > div[3] > div[1] > div > div[2] > a[2]

$ java -jar app.jar sample-0-origin.html sample-2-container-and-clone.html 
html > body > div > div > div[3] > div[1] > div > div[2] > div > a

$ java -jar app.jar sample-0-origin.html sample-3-the-escape.html 
html > body > div > div > div[3] > div[1] > div > div[3] > a

$ java -jar app.jar sample-0-origin.html sample-4-the-mash.html 
html > body > div > div > div[3] > div[1] > div > div[3] > a
```   

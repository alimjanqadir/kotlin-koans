package ii_collections

fun example9(): Int {
    val result = listOf(1, 2, 3, 4).fold(3, { partResult, element ->
        element * partResult
    })
    result == 24
    return result
}

// The same as
fun whatFoldDoes(): Int {
    var result = 1
    listOf(1, 2, 3, 4).forEach { element -> result = element * result }
    return result
}

fun Shop.getSetOfProductsOrderedByEachCustomer(): Set<Product> {
    // Return the set of products that were ordered by each of the customers
    return customers.fold(allOrderedProducts, { orderedByAll, customer ->
        orderedByAll.intersect(customer.orderedProducts)
    })
}
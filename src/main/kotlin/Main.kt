
fun main(args: Array<String>) {
    //input arguments
    val input = "param1=42&param2=54&param3=ololo&param3=trololo&param4=5&param5"
    //object creation
    val queryParamsImpl = QueryParamsImpl()
    queryParamsImpl.execute(input)
}

internal interface QueryParams {
    fun execute(query: String) : Map<String, List<String>>
}

class QueryParamsImpl : QueryParams {
    override fun execute(query: String): Map<String, List<String>> {
        val dat = query
            .split("&")
            .mapNotNull { keyAndValue ->
                val keyAndValueList = keyAndValue.split("=")
                keyAndValueList.takeIf { it.size == 2 }
            }
            .mapNotNull { keyAndValueList ->
                val (key, value) = keyAndValueList
                (key to value.trim()).takeIf { value.isNotBlank() }
            }
            .groupBy { (key, _) -> key }
            .map { mapEntry ->
                mapEntry.key to mapEntry.value.map { it.second }
            }
            .toMap()
        return dat;
    }
}
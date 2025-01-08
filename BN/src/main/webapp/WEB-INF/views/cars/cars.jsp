<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Car List</title>
</head>
<body>

<h1>Car List</h1>
<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Model</th>
        <th>Brand</th>
        <th>Year</th>
        <th>Available From</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="car" items="${cars}">
        <tr>
            <td>${car.details.id}</td>
            <td>${car.model}</td>
            <td>${car.brand}</td>
            <td>${car.year}</td>
            <td>${car.availableFrom}</td>
            <td>
                <a href="/car/edit/${car.carid}">Edit</a>

                <!-- Delete form -->
                <form action="${pageContext.request.contextPath}/car/delete/${car.carid}" method="post" style="display:inline;">
                    <input type="hidden" name="_method" value="delete" />
                    <input type="submit" value="Delete" />
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<a href="/car/add">Add New Car</a>

</body>
</html>

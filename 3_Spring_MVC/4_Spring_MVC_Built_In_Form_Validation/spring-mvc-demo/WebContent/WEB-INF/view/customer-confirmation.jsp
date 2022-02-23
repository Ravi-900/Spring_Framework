<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>

<html>

<head>
	<title>Customer Confirmation</title>
</head>

<body>

	The customer is confirmed: ${customer.firstName} ${customer.lastName}
	
	<br><br>
	Passes: ${customer.freePasses}
	
	<br><br>
    Postal: ${customer.postalCode}

</body>

</html>











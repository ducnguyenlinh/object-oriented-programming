<?php

// array for JSON response

$response = array();

// check for required fields

if (isset($_POST['maSV'])) {

    $maSV = $_POST['maSV'];

    // include db connect class

    require_once __DIR__ . '/db_connect.php';

    // connecting to db

    $db = new DB_CONNECT();

       $con = $db->getConnection();

    // mysql update row with matched http_persistent_handles_ident()

    $result = mysqli_query($con, "DELETE FROM nguoidung WHERE maSV = $maSV") or die(mysqli_error($con));

    // check if row deleted or not

    if (mysqli_affected_rows($con) > 0) {

        // successfully updated

        $response["success"] = 1;

        $response["message"] = "Product successfully deleted";

        // echoing JSON response

        echo json_encode($response);

    } else {

        // no product found

        $response["success"] = 0;

        $response["message"] = "No product found";

        // echo no users JSON

        echo json_encode($response);

    }

} else {

    // required field is missing

    $response["success"] = 0;

    $response["message"] = "Required field(s) is missing";

    // echoing JSON response

    echo json_encode($response);

}
?>
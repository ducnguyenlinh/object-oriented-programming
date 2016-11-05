<?php

$response = array();

// include db connect class

require_once __DIR__ . '/db_connect.php';

// connecting to db

$db = new DB_CONNECT();

$con = $db->getConnection();

// get all accounts from accounts table

$result = mysqli_query($con, "SELECT * FROM sach") or die(mysqli_error($con));

// check for empty result

if (mysqli_num_rows($result) > 0) {

    // looping through all results

    // accounts node

    $response["books"] = array();

    while ($row = mysqli_fetch_array($result)) {

        // temp user array

        $book_array = array();

        $book_array["maSach"]    =  $row["maSach"];

        $book_array["tenSach"]   =  $row["tenSach"];

        $book_array["tenTG"]     =  $row["tenTG"];

        $book_array["namXB"]     =  $row["namXB"];

        // push single book_array into final response array

        array_push($response["books"], $book_array);

    }

    // success

    $response["success"] = 1;

    // echoing JSON response

    echo json_encode($response);

} else {

    // no accounts found

    $response["success"] = 0;

    $response["message"] = "No products found";

    // echo no users JSON

    echo json_encode($response);

}
?>
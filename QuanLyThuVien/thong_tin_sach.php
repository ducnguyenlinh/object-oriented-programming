<?php
$response = array();

// include db connect class

require_once __DIR__ . '/db_connect.php';

// connecting to db

$db = new DB_CONNECT();

$con = $db->getConnection();

// check for post data

if (isset($_GET['maSach'])) {

    $maSach = $_GET['maSach'];

    // get a book_array from products table

    $result = mysqli_query($con, "SELECT * FROM sach WHERE maSach = $maSach") or die(mysqli_error($con));
    

    if (!empty($result)) {

        // check for empty result

        if (mysqli_num_rows($result) > 0) {

            $result = mysqli_fetch_array($result);

            $book_array = array();

            $book_array["maSach"] = $result["maSach"];

            $book_array["tenSach"] = $result["tenSach"];

            $book_array["tenTG"] = $result["tenTG"];

            $book_array["namXB"] = $result["namXB"];

            // success

            $response["success"] = 1;

            // user node

            $response["books"] = array();

            array_push($response["books"], $book_array);

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
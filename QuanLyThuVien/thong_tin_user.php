<?php
$response = array();

// include db connect class

require_once __DIR__ . '/db_connect.php';

// connecting to db

$db = new DB_CONNECT();

$con = $db->getConnection();

// check for post data

if (isset($_GET['maSV'])) {

    $maSV = $_GET['maSV'];

    // get a user_array from products table

    $result = mysqli_query($con, "SELECT * FROM nguoidung WHERE maSV = $maSV") or die(mysqli_error($con));
    

    if (!empty($result)) {

        // check for empty result

        if (mysqli_num_rows($result) > 0) {

            $result = mysqli_fetch_array($result);

            $user_array = array();

            $user_array["maSV"] = $result["maSV"];

            $user_array["tenND"] = $result["tenND"];

            $user_array["ngaySinh"] = $result["ngaySinh"];

            $user_array["ngayDK"] = $result["ngayDK"];

            // success

            $response["success"] = 1;

            // user node

            $response["users"] = array();

            array_push($response["users"], $user_array);

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
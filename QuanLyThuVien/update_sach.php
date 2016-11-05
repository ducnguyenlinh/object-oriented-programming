<?php
$response = array();

// check for required fields

if (isset($_POST['maSach']) && isset($_POST['tenSach']) && isset($_POST['tenTG']) && isset($_POST['namXB'])) {

    $maSach  = $_POST['maSach'];

    $tenSach = $_POST['tenSach'];

    $tenTG   = $_POST['tenTG'];

    $namXB   = $_POST['namXB'];

    // include db connect class

    require_once __DIR__ . '/db_connect.php';

    // connecting to db

    $db = new DB_CONNECT();

       $con = $db->getConnection();

    // mysql update row with matched maSach

    $result = mysqli_query($con, "UPDATE sach SET tenSach = '$tenSach', tenTG = '$tenTG', namXB = '$namXB' WHERE maSach = $maSach") or die(mysqli_error($con));

    // check if row inserted or not

    if ($result) {

        // successfully updated

        $response["success"] = 1;

        $response["message"] = "Product successfully updated.";

        // echoing JSON response

        echo json_encode($response);

    } else {

    }

} else {

    // required field is missing

    $response["success"] = 0;

    $response["message"] = "Required field(s) is missing";

    // echoing JSON response

    echo json_encode($response);

}

?>
<?php
 
$response = array();

// check for required fields

if (isset($_POST['maSach']) && isset($_POST['tenSach']) && isset($_POST['tenTG']) && isset($_POST['namXB'])) {

    $maSach = $_POST['maSach'];

    $tenSach = $_POST['tenSach'];

    $tenTG = $_POST['tenTG'];

    $namXB = $_POST['namXB'];


    // include db connect class

    require_once __DIR__ . '/db_connect.php';
   
    // connecting to db

    $db = new DB_CONNECT();

       $con = $db->getConnection();

    // mysql inserting a new row

    $result = mysqli_query($con, "INSERT INTO sach(maSach,tenSach,tenTG,namXB) VALUES('$maSach', '$tenSach', '$tenTG', '$namXB')") or die(mysqli_error($con));

    // check if row inserted or not

    if ($result) {

        // successfully inserted into database

        $response["success"] = 1;

        $response["message"] = "Product successfully created.";

        // echoing JSON response

        echo json_encode($response);

    } else {

        // failed to insert row

        $response["success"] = 0;

        $response["message"] = "Oops! An error occurred.";

        // echoing JSON response

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
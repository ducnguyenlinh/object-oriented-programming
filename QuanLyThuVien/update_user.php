<?php
$response = array();

// check for required fields

if (isset($_POST['maSV']) && isset($_POST['tenND']) && isset($_POST['ngaySinh']) && isset($_POST['ngayDK'])) {

    $maSV  = $_POST['maSV'];

    $tenND = $_POST['tenND'];

    $ngaySinh   = $_POST['ngaySinh'];

    $ngayDK   = $_POST['ngayDK'];

    // include db connect class

    require_once __DIR__ . '/db_connect.php';

    // connecting to db

    $db = new DB_CONNECT();

       $con = $db->getConnection();

    // mysql update row with matched maSV

    $result = mysqli_query($con, "UPDATE nguoidung SET tenND = '$tenND', ngaySinh = '$ngaySinh', ngayDK = '$ngayDK' WHERE maSV = $maSV") or die(mysqli_error($con));

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
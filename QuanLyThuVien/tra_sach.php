<?php
$response = array();

if (isset($_POST['maSach']) && isset($_POST['maSV']) && isset($_POST['ngayTra'])) {

    $maSach = $_POST['maSach'];

    $maSV = $_POST['maSV'];

    $ngayTra = $_POST['ngayTra'];


    // include db connect class

    require_once __DIR__ . '/db_connect.php';
   
    // connecting to db

    $db = new DB_CONNECT();

       $con = $db->getConnection();

    // mysql inserting a new row

    $result = mysqli_query($con, "INSERT INTO tra(maSach,maSV,ngayTra) VALUES('$maSach', '$maSV', '$ngayTra')") or die(mysqli_error($con));

    $result1 = mysqli_query($con,"SELECT * FROM muon WHERE muon.maSach = $maSach") or die(mysqli_error($con));
    $row = mysqli_fetch_array($result1);
    $ngayMuon1 = $row['ngayMuon'];

    $result2 = mysqli_query($con, "INSERT INTO history(maSach,maSV,ngayMuon) VALUES('$maSach', '$maSV', '$ngayMuon1')") or die(mysqli_error($con));

    $result3 = mysqli_query($con, "DELETE FROM muon WHERE maSach = $maSach") or die(mysqli_error($con));    

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

}
?>
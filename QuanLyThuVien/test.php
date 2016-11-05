<?php
// include db connect class

    require_once __DIR__ . '/db_connect.php';
   
    // connecting to db

    $db = new DB_CONNECT();

       $con = $db->getConnection();
        $ngayMuon = mysqli_query($con,"SELECT * FROM muon WHERE muon.maSach = '8'") ;
        $row = mysqli_fetch_array($ngayMuon);
        $a = $row['ngayMuon'];

        $result2 = mysqli_query($con, "INSERT INTO history(maSach,maSV,ngayMuon) VALUES('8', '8', '$a')");

        ?>
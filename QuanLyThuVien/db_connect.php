
<?php
class DB_CONNECT {

       private $con;

    // constructor

    function __construct() {

        // connecting to database

        $this->connect();

    }

    // destructor

    function __destruct() {

        // closing db connection

        $this->close();

    }

    /**
     * Function to connect with database
     */

    function connect() {

        require_once __DIR__ . '/db_config.php';

              $this->con = mysqli_connect(DB_SERVER, DB_USER, DB_PASSWORD) or die(mysqli_error($this->con));

              $db = mysqli_select_db($this->con, DB_DATABASE) or die(mysqli_error($this->con));

    }

    /**
     * Function to close db connection
     */

    function close() {

        // closing db connection

        mysqli_close($this->con);

    }

    function getConnection() {

              return $this->con;

    }

}
?>
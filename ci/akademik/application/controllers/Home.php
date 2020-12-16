

<?php

defined('BASEPATH') OR exit('No direct script access allowed');

require APPPATH . '/libraries/REST_Controller.php';
// use Restserver\Libraries\REST_Controller;

class Home extends REST_Controller {

    function __construct($config = 'rest') {
        parent::__construct($config);
        $this->load->database();
    }

    //Menampilkan data kontak
    function index_get() {
        $id = $this->get('noteID');
        if ($id == '') {
            $kontak = $this->db->get('note')->result();
        } else {
            $this->db->where('noteID', $id);
            $kontak = $this->db->get('note')->result();
		}
		// echo $kontak;
        $this->response($kontak, 200);
    }


   //Mengirim atau menambah data kontak baru
   function index_post() {
    $data = array(
                'noteID'           => $this->post('noteID'),
                'noteTitle'          => $this->post('noteTitle'),
				'createDateTime'    => $this->post('createDateTime'),
				'lastEditDateTime'    => $this->post('lastEditDateTime')
			);
    $insert = $this->db->insert('note', $data);
    if ($insert) {
        $this->response($data, 200);
    } else {
        $this->response(array('status' => 'fail', 502));
    }
}

//Memperbarui data kontak yang telah ada
function index_put() {
    $id = $this->put('noteID');
    $data = array(
		'noteID'           => $this->put('noteID'),
		'noteTitle'          => $this->put('noteTitle'),
		'createDateTime'    => $this->put('createDateTime'),
		'lastEditDateTime'    => $this->put('lastEditDateTime')
			);
    $this->db->where('noteID', $id);
    $update = $this->db->update('note', $data);
    if ($update) {
        $this->response($data, 200);
    } else {
        $this->response(array('status' => 'fail', 502));
    }
}

 //Menghapus salah satu data kontak
 function index_delete() {
    $id = $this->delete('noteID');
    $this->db->where('noteID', $id);
    $delete = $this->db->delete('note');
    if ($delete) {
        $this->response(array('status' => 'success'), 201);
    } else {
        $this->response(array('status' => 'fail', 502));
    }
}

}
?>

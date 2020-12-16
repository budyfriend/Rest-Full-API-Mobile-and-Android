

<?php

defined('BASEPATH') OR exit('No direct script access allowed');

require APPPATH . '/libraries/REST_Controller.php';
// use Restserver\Libraries\REST_Controller;

class Mahasiswa extends REST_Controller {

    function __construct($config = 'rest') {
        parent::__construct($config);
        $this->load->database();
    }

    //Menampilkan data kontak
    function index_get() {
        $id = $this->get('id');
        if ($id == '') {
            $kontak = $this->db->get('mahasiswa')->result();
        } else {
            $this->db->where('id', $id);
            $kontak = $this->db->get('mahasiswa')->result();
		}
		// echo $kontak;
        $this->response($kontak, 200);
    }


   //Mengirim atau menambah data kontak baru
   function index_post() {
    $data = array(
                'id'           => $this->post('id'),
                'nama'          => $this->post('nama'),
				'jurusan'    => $this->post('jurusan'),
				'semester'    => $this->post('semester')
			);
    $insert = $this->db->insert('mahasiswa', $data);
    if ($insert) {
        $this->response($data, 200);
    } else {
        $this->response(array('status' => 'fail', 502));
    }
}

//Memperbarui data kontak yang telah ada
function index_put() {
    $id = $this->put('id');
    $data = array(
		'id'           => $this->put('id'),
		'nama'          => $this->put('nama'),
		'jurusan'    => $this->put('jurusan'),
		'semester'    => $this->put('semester')
			);
    $this->db->where('id', $id);
    $update = $this->db->update('mahasiswa', $data);
    if ($update) {
        $this->response($data, 200);
    } else {
        $this->response(array('status' => 'fail', 502));
    }
}

 //Menghapus salah satu data kontak
 function index_delete() {
    $id = $this->delete('id');
    $this->db->where('id', $id);
    $delete = $this->db->delete('mahasiswa');
    if ($delete) {
        $this->response(array('status' => 'success'), 200);
    } else {
        $this->response(array('status' => 'fail', 502));
    }
}

}
?>

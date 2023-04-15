
$('#editModal').on('show.bs.modal', function (event) {
    var button = $(event.relatedTarget) // Button that triggered the modal

    var id = button.data('id'); // Extract info from data-* attributes
    var name = button.data('name');
    var lastname = button.data('lastname');
    var age = button.data('age');
    var email = button.data('email');
    var roles = button.data('roles');

    var modal = $(this);
    modal.find('#edit_id').val(id);
    modal.find('#edit_name').val(name);
    modal.find('#edit_lastname').val(lastname);
    modal.find('#edit_age').val(age);
    modal.find('#edit_email').val(email);

})

$('#deleteModal').on('show.bs.modal', function (event) {
    var button = $(event.relatedTarget) // Button that triggered the modal
    var id = button.data('id'); // Extract info from data-* attributes
    var name = button.data('name');
    var lastname = button.data('lastname');
    var age = button.data('age');
    var email = button.data('email');
    var roles = button.data('roles');

    var modal = $(this);
    modal.find('#delete_id').val(id);
    modal.find('#delete_name').val(name);
    modal.find('#delete_lastname').val(lastname);
    modal.find('#delete_age').val(age);
    modal.find('#delete_email').val(email);

    // modal.find('#delete_roles option:selected').removeAttr('selected');
    //
    // // Установить выбранные значения для ролей
    // roles.forEach(function(role) {
    //     modal.find('#delete_roles option[value="' + role + '"]').prop('selected', true);
    // });

})

$(document).ready(function () {
    getUsers();
})


 function getUsers () {
     let url = '/api/users'


     $('#tbody').empty()
     fetch("http://localhost:8080/api/users")
         .then(res => res.json())
         .then(data => {
             data.forEach(user => {
                 let tableWithUsers = `$(
                        <tr>
                            <td>${user.id}</td>
                            <td>${user.name}</td>
                            <td>${user.lastname}</td>                                               
                            <td>${user.age}</td>
                            <td>${user.email}</td>
                            <td>${user.roles.map(role => " " + role.rolename )}</td>
                            <td>
                                <a class="btn btn-info text-white"
                                data-toggle="modal"  
                                data-target="#editModal"
                                data-name="${user.name}"
                                data-lastname="${user.lastname}"
                                data-age="${user.age}" 
                                data-email="${user.email}"
                                data-roles="${user.roles}"
                                data-id="${user.id}"
                                > Edit </a>
                            </td>
                            <td>
                                <a  class="btn btn-danger delete-button text-white"
                                    data-toggle="modal" data-target="#deleteModal" data-id="${user.id}"
                                    data-name="${user.name}" data-lastname="${user.lastname}"
                                    data-age="${user.age}" data-email="${user.email}"
                                    data-roles="${user.roles}">
                                    delete
                                </a>
                            </td>
                        </tr>)`;
                 $('#tbody').append(tableWithUsers);
             })
         })
 }


//

// function editUser() {
//     var formEdit = $(this).data('editForm');
//     console.log(formEdit.data('edit_name'));
// }



var formEdit = document.forms['editForm']
function editUser() {
    event.preventDefault();
    let id = document.getElementById('edit_id').value;
    let name = document.getElementById('edit_name').value;
    let lastname = document.getElementById('edit_lastname').value;
    let age = document.getElementById('edit_age').value;
    let email = document.getElementById('edit_email').value;
    let roles = $("#edit_roles").val()

    for (let i = 0; i < roles.length; i++) {
        if (roles[i] === 'Admin') {
            roles[i] = {
                'id': 1,
                'rolename':'Admin',
                'role': 'ROLE_ADMIN',
                "authority": "ROLE_ADMIN"
            }
        }
        if (roles[i] === 'User') {
            roles[i] = {
                'id': 2,
                'rolename':'User',
                'role': 'ROLE_USER',
                "authority": "ROLE_USER"
            }
        }
    }
        // alert('PUT ' + formEdit.id.value)
        fetch("http://localhost:8080/api/edit", {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json;charset=UTF-8'
            },
            body: JSON.stringify({
                'id': id,
                'name': name,
                'lastname': lastname,
                'password':'',
                'age': age,
                'email': email,
                'roles': roles
            })
        }).then(() => {
            $('#editFormCloseButton').click();
            getUsers();
        });
}

function deleteUser(){

    let id = document.getElementById("delete_id").value
    console.log(id)

    fetch("http://localhost:8080/api/delete/" + id, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json;charset=UTF-8'
        }
    }).then(() => {
        $('#deleteFormCloseButton').click();
        getUsers();
    });
}

function addUser(){

    let name = document.getElementById('add_name').value
    let lastname = document.getElementById('add_lastname').value
    let age = document.getElementById('add_age').value
    let email = document.getElementById('add_email').value
    let password = document.getElementById('add_password').value
    let roles = $("#add_roles").val()
    for (let i = 0; i < roles.length; i++) {
        if (roles[i] === 'Admin') {
            roles[i] = {
                'id': 1,
                'rolename':'Admin',
                'role': 'ROLE_ADMIN',
                "authority": "ROLE_ADMIN"
            }
        }
        if (roles[i] === 'User') {
            roles[i] = {
                'id': 2,
                'rolename':'User',
                'role': 'ROLE_USER',
                "authority": "ROLE_USER"
            }
        }
    }

    fetch("http://localhost:8080/api/addUser", {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=UTF-8'
        },
        body: JSON.stringify({
            'name': name,
            'lastname': lastname,
            'password':password,
            'age': age,
            'email': email,
            'roles': roles
        })
    })
        .then(() => {
            document.getElementById('nav-users=tab').click()
            getUsers()
        })

}






$('#editModal').on('show.bs.modal', function (event) {
    var button = $(event.relatedTarget) // Button that triggered the modal
    // var id = button.data('id') // Extract info from data-* attributes
    // var name = button.data('name')
    // var modal = $(this)
    // modal.find('#id').val(id)
    // modal.find('#name').val(name)

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
    // // Установите выбранные значения для ролей
    // roles.forEach(function(role) {
    //     modal.find('#delete_roles option[value="' + role + '"]').prop('selected', true);
    // });

})
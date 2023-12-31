/*
 * Checks whether the password and password-confirm fields match
 */
$(document).on('focusout', '.password-input,.password-confirm', function(e) {
    var $form = $(this).closest("form");
    var $password = $form.find(".password-input");
    var $passwordConfirm = $form.find(".password-confirm");

    if ($password.val().trim() == '') {
        return false;
    }

    if ($password.val() !== $passwordConfirm.val()) {
        $passwordConfirm.closest('.form-group').addClass('has-error');
        $password.closest('.form-group').addClass('has-error');
        $passwordConfirm.next('p.help-block').html('<strong>Erro</strong>: as senhas não coincidem!');
        $form.find("button,input[type='submit']").prop('disabled', true);
    } else {
        $passwordConfirm.closest('.form-group').removeClass('has-error').addClass('has-success');
        $password.closest('.form-group').removeClass('has-error').addClass('has-success');
        $passwordConfirm.next('p.help-block').html('');
        $form.find("button,input[type='submit']").prop('disabled', false);
    }
});

function deleteUser(e) {
    e.preventDefault();
    $('.link_confirmacao_excluir_usuario').attr('href', $(this).data('href'));
    $('.modal_excluir_usuario').modal();
}

function deleteUsers(e) {
    e.preventDefault();
    $('.form_excluir_usuarios').submit();
}
function showAlertBJ() {
	resultado = document.getElementById('result_id').value;
	
	if (resultado == 21) {
		alert("Voce ganhou");
	}else if (resultado > 15 && resultado <= 21) {
		alert("Voce ganhou");
	}else {
 		alert("Voce perdeu");
	}
}
function showAlertSM(){
	result1 = document.getElementById('resultA_id').value;
	result2 = document.getElementById('resultB_id').value;
	result3 = document.getElementById('resultC_id').value;
	
	if (result1 == result2 && result1 == result3) {
	alert("Voce ganhou");
	}else {
	 alert("Voce perdeu");
	}
}
function readUser(e) {
    e.preventDefault();
    $.get($(this).data('href'), function (data) {
        var usuario = JSON.parse(data);
        var avatar = 'default_avatar.png';
        var $modal = $('.modal-visualizar-usuario');

        $modal.find('.p_id').html('<strong>ID: </strong>' + usuario.id);
        $modal.find(".p_login").html('<strong>Login: </strong>' + usuario.login);
        $modal.find('.p_nome').html('<strong>Nome: </strong>' + usuario.nome);
        $modal.find('.p_nascimento').html('<strong>Data de nascimento: </strong>' + usuario.nascimento);
        if (usuario.avatar) {
            avatar = usuario.avatar;
        }
        $modal.find('.usuario-img').prop('src', $.url("//img/" + avatar));
        
        $modal.modal();
    });
}

$(document).on('focusout', '#usuario-login', function (e) {
    var $input = $(this);
    if ($("#usuario-login").val() == $(this).data('value')) {
        var $formGroup = $input.parents(".form-group").first();
        if ($formGroup.hasClass("has-error")) {
            $formGroup.removeClass("has-error");
        }
        $input.next("p").html("");
    }
    else {
        $.post($.url("//user/checkLogin"), { login: $("#usuario-login").val() }, function(data) {
            var $formGroup = $input.parents(".form-group").first();
            if (data.status == "USADO") {
                if (!$formGroup.hasClass("has-error")) {
                    $formGroup.addClass("has-error");
                }
                $input.next("p").html("O login escolhido existe. Por favor, tente outro.");
            } else {
                if ($formGroup.hasClass("has-error")) {
                    $formGroup.removeClass("has-error");
                }
                $input.next("p").html("");
            }
        });
    }
});


$(document).ready(function () {
    $(document).on('click', '.link_excluir_usuario', deleteUser);
    $(document).on('click', '.button_confirmacao_excluir_usuarios', deleteUsers);
    $(document).on('click', '.link_visualizar_usuario', readUser);    
    $("*[data-toggle='tooltip']").tooltip({
        'container': 'body'
    });
});
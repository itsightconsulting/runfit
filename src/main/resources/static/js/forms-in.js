function validUniqueEmailOrUsernameOrNomPag(input, pathURLDiff){
    input.setAttribute('disabled', 'disabled');
    input.parentElement.previousElementSibling.previousElementSibling.classList.add('hidden');
    input.parentElement.previousElementSibling.classList.remove('hidden');
    $.ajax({
        type: 'GET',
        contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
        url: _ctx+'p/validacion-'+pathURLDiff,
        blockLoading: false,
        noOne: true,
        data: {valor: input.value},
        dataType: 'json',
        success: function(res){
            input.parentElement.previousElementSibling.classList.add('hidden');
            if(!res){
                if(verifiedNames){
                    verifiedNames.push(input.value);
                }
                input.parentElement.previousElementSibling.previousElementSibling.classList.remove('hidden');
            }else{
                if(pathURLDiff === 'username'){
                    $(input).rules('add', {dynUnique: input.value, messages:{dynUnique: 'El nombre de usuario ingresado ya se encuentra registrado'}});
                }
                else if(pathURLDiff === 'nompag'){
                    $(input).rules('add', {dynUnique: input.value, messages:{dynUnique: 'El nombre de página ingresado ya se encuentra registrado'}});
                }
                else{
                    $(input).rules('add', {dynUnique: input.value});
                }
                $(input).valid();
            }
            input.removeAttribute('disabled');
        },
        error: (err)=>{
            exception(err);
        }
    })
}
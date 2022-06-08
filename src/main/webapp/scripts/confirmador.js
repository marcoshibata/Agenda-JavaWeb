/**
 * Confirmacao de exclusao de um contato
 */
function confirmar(idcon){
	let resposta = confirm("Confirmação deste contato?")
	if (resposta === true){
		window.location.href="delete?idcon="+idcon
	}
}
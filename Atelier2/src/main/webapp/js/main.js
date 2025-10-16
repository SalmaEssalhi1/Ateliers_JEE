$(function() {
  // Animation d'apparition
  $('.animate-fadein').css({opacity:0,top:'30px'}).animate({opacity:1,top:0}, 700);

  // Validation simple formulaire
  $('#produitForm').on('submit', function(e) {
    let valid = true;
    $(this).find('input[required]').each(function() {
      if (!$(this).val()) {
        valid = false;
        $(this).addClass('input-error');
      } else {
        $(this).removeClass('input-error');
      }
    });
    if (!valid) {
      e.preventDefault();
      showMessage('Veuillez remplir tous les champs.', 'error');
    }
  });

  // Message dynamique
  function showMessage(msg, type) {
    $('#form-message').removeClass('success error').addClass(type).text(msg).fadeIn();
    setTimeout(function() { $('#form-message').fadeOut(); }, 3000);
  }

  // Effet hover sur les boutons
  $('.btn').hover(function() {
    $(this).css('box-shadow','0 2px 8px #1976d233');
  }, function() {
    $(this).css('box-shadow','none');
  });
});

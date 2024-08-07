function syncValues() {
  var inputs = document.querySelectorAll('input[type="text"]');

  inputs.forEach(input => {
    input.addEventListener('input', function() {

      var idParts = this.id.match(/item(\d)(\d)/);
      if (idParts) {
        var prntRowNumber = idParts[1];
        var rowNumber = idParts[2];

        if (prntRowNumber === '5' && rowNumber !== '5') {
          var targetId = 'item' + rowNumber + prntRowNumber;
          var targetInput = document.getElementById(targetId);
          if (targetInput) {
            targetInput.value = this.value;
          }
        } else if(rowNumber === '5' && prntRowNumber !== '5'){
          var targetId = 'item' + rowNumber + prntRowNumber;
          var targetInput = document.getElementById(targetId);
          if (targetInput) {
            targetInput.value = this.value;
          }
        }
      }
    });
  });
}
window.onload = syncValues;

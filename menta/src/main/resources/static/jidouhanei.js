  function syncValues() {
  const inputBox = document.getElementById("inability");
  const outputBox = document.getElementById("outability");
   const emailBox = document.getElementById("email");
  inputBox.addEventListener("input", function () {
    outputBox.value =inputBox.value;
     emailBox.value =inputBox.value;
  });
   outputBox.addEventListener("input", function () {
        inputBox.value =outputBox.value;
      });
  }
  window.onload = syncValues;

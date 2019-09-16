function loadForm(url) {
  window.location = url;
}

function updateApplication(id) {
  window.location = 'application-update?applicant_id=' + id;
}

function deleteApplication(id) {
  $('#main').load('/application-delete?applicant_id=' + id);
}

function doSaveForm(event, url, formId) {
  event.preventDefault();
  $.ajax({
    url: url,
    method: 'post',
    data: $('#' + formId).serialize(),
    dataType: 'json',
    success: function (json) {
      if (json.location != '' && json.location != 'undefined') {
        document.location = json.location;
      } else {
        alert(json.msg)
      }
    }
  });
}

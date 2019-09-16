function loadForm(url) {
  $('#main').load(url);
}

function updateApplication(id) {
  $('#main').load('/application-update?applicant_id=' + id);
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
        $('#main').load(json.location);
      } else {
        alert(json.msg)
      }
    }
  });
}

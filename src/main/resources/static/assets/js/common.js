function loadForm(url) {
  window.location = url;
}

function updateApplication(id) {
  window.location = 'application-update?applicant_id=' + id;
}

function archiveApplication(id) {
  document.location = 'application-archive?applicant_id=' + id;
}

function restoreArchive(id) {
  document.location = 'archive-restore?applicant_id=' + id;
}

function deleteArchive(id) {
  document.location = 'archive-delete?applicant_id=' + id;
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

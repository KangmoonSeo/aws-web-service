let index = {
    init: function () {
        let _this = this;
        $('#btn-save').on('click', (e) => {
            e.preventDefault();
            _this.save();
        });
        $('#btn-update').on('click', (e) => {
            e.preventDefault();
            _this.update();
        });
        $('#btn-delete').on('click', (e) => {
            e.preventDefault();
            _this.delete();
        });
    },

    save: function () {
        if (!$('#title').get(0).checkValidity() || !$('#author').get(0).checkValidity() || !$('#author').get(0).checkValidity()) {
            return;
        }

        let data = {
            title: $('#title').val(),
            author: $('#author').val(),
            content: $('#content').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/posts',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(
            () => {
                alert('글이 등록되었습니다.');
                location.href = "/";
            }
        ).fail((error) => {
            alert(JSON.stringify(error));
        });
    },

    update: function () {
        let data = {
            title: $('#title').val(),
            content: $('#content').val()
        };
        let id = $('#id').val();

        if (!confirm("수정하시겠습니까?")) return;
        $.ajax({
            type: 'PUT',
            url: '/api/v1/posts/' + id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(
            () => {
                alert('글이 수정되었습니다.');
                location.href = "/";
            }
        ).fail((error) => {
            alert(JSON.stringify(error));
        });
    },

    delete: function () {
        let id = $('#id').val();

        if (!confirm("삭제하시겠습니까?")) return;
        $.ajax({
            type: 'DELETE',
            url: '/api/v1/posts/' + id,
        }).done(
            () => {
                alert('글이 삭제되었습니다.');
                location.href = "/";
            }
        ).fail((error) => {
            alert(JSON.stringify(error));
        });
    },
};
index.init();

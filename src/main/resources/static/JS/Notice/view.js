$(document).ready(() => {
    $(".returnbtn").on("click", () => {
        history.back();
    });

    $(".commentaddbtn").on("click", () => {
        $.ajax({
            url: '/commentadd',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                userid: $(".userid").val(),
                nick: $(".nick").val(),
                comment: $(".commentadd").val(),
                noticeidx: $(".noticeidx").val()
            }),
            success: (data) => {
                $(".comment").append(`
                    <div id="${data.commentidx.trim()}" class="mb-2">
                        <img style="width:20px; height:20px;" src="/iconimg?userid=${data.nick.trim()}" alt="사용자 아이콘" class="rounded-circle me-2">
                        <span class="fw-bold">${data.userid.trim()}</span>
                        <span class="text-muted me-2">${data.regdate.trim()}</span>
                        <span>${$(".commentadd").val()}</span>
                        <button class="commentmodify btn btn-outline-secondary btn-sm" data-idx="${data.commentidx.trim()}" data-content="${$(".commentadd").val()}">수정</button>
                        <button class="commentdelete btn btn-outline-danger btn-sm" data-idx="${data.commentidx.trim()}">삭제</button>
                    </div>
                `);
                $(".commentadd").val("");
            },
            error: () => {
                alert('오류가 발생했습니다');
            }
        });
    });

    $(document).on("click", ".commentmodify", (event) => {
        const commentidx = $(event.currentTarget).data("idx");
        const commentcontent = $(event.currentTarget).data("content");
        $("#" + commentidx).html(`
            <input type="text" class="contentmodify${commentidx}" style="width:500px" value="${commentcontent}">
            <button type="button" class="commentmodifytrue btn btn-primary" data-idx="${commentidx}">수정</button>
            <button type="button" class="commentmodifyreturn btn btn-secondary" data-idx="${commentidx}">취소</button>
        `);
    });

    $(document).on("click", ".commentmodifyreturn", (event) => {
        const idxValue = $(event.currentTarget).data("idx");

        $.ajax({
            url: '/commentreturn',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                idx: idxValue
            }),
            success: (data) => {
                $("#" + idxValue).html(`
                    <div id="${data.commentidx.trim()}" class="mb-2">
                        <img style="width:20px; height:20px;" src="/iconimg?userid=${data.userid.trim()}" alt="사용자 아이콘" class="rounded-circle me-2">
                        <span class="fw-bold">${data.nick.trim()}</span>
                        <span class="text-muted me-2">${data.regdate.trim()}</span>
                        <span>${data.comment.trim()}</span>
                        <button class="commentmodify btn btn-outline-secondary btn-sm" data-idx="${data.commentidx.trim()}" data-content="${data.comment.trim()}">수정</button>
                        <button class="commentdelete btn btn-outline-danger btn-sm" data-idx="${data.commentidx.trim()}">삭제</button>
                    </div>
                `);
            },
            error: () => {
                alert('오류가 발생했습니다');
            }
        });
    });

    $(document).on("click", ".commentmodifytrue", (event) => {
        const commentidx = $(event.currentTarget).data("idx");
        const commentcontent = $(`.contentmodify${commentidx}`).val();

        $.ajax({
            url: '/commentmodify',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                idx: commentidx,
                content: commentcontent
            }),
            success: (data) => {
                $("#" + commentidx).html(`
                    <div id="${data.commentidx.trim()}" class="mb-2">
                        <img style="width:20px; height:20px;" src="/iconimg?userid=${data.userid.trim()}" alt="사용자 아이콘" class="rounded-circle me-2">
                        <span class="fw-bold">${data.nick.trim()}</span>
                        <span class="text-muted me-2">${data.regdate.trim()}</span>
                        <span>${data.comment.trim()}</span>
                        <button class="commentmodify btn btn-outline-secondary btn-sm" data-idx="${data.commentidx.trim()}" data-content="${data.comment.trim()}">수정</button>
                        <button class="commentdelete btn btn-outline-danger btn-sm" data-idx="${data.commentidx.trim()}">삭제</button>
                    </div>
                `);
            },
            error: () => {
                alert('오류가 발생했습니다');
            }
        });
    });

    $(document).on("click", ".commentdelete", (event) => {
        const commentidx = $(event.currentTarget).data("idx");
        console.log(commentidx);
        $.ajax({
            url: '/commentdelete',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                idx: commentidx
            }),
            success: () => {
                $("#" + commentidx).html(`
                    <div id="${commentidx}" class="mb-2">
                        <span>삭제된 댓글입니다</span>
                    </div>
                `);
            }
        });
    });
});

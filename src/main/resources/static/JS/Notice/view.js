$(document).ready(()=>{
    $(".returnbtn").on("click",()=>{
            history.back();
    })

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
                    <div id="${data.commentidx.trim()}">
                        <img style="width:20px; height:20px;" src="/iconimg?userid=${data.userid.trim()}" alt="사용자 아이콘">
                        <span>${$(".nick").val()}</span>
                        |
                        <span>${$(".commentadd").val()}</span>
                        |
                        <span>${data.regdate.trim()}</span>
                        <button class="commentmodify" data-idx="${data.commentidx.trim()}" data-content="${$(".commentadd").val()}">수정</button>
                        <button class="commentdelete" data-idx="${data.commentidx.trim()}">삭제</button>
                    </div>
                `);
            },
            error: () => {
                alert('오류가 발생했습니다');
            }
        });
    });

    $(".commentdelete").on("click",(event)=>{
            const commentIdx = $(event.currentTarget).data("idx");
            console.log(commentIdx);
    })
   $(document).on("click", ".commentmodify", (event) => {
       const commentIdx = $(event.currentTarget).data("idx");
       console.log(commentIdx);
       const commentcontent = $(event.currentTarget).data("content");
       console.log(commentcontent);
       $("#" + commentIdx).html(
           `<input type="text" class="contentmodify" value="${commentcontent}">
            <button type="button" class="commentmodifytrue" data-idx="${commentIdx}">수정</button>
            <button type="button" class="commentmodifyreturn" data-idx="${commentIdx}">취소</button>`
       );
   });

   $(document).on("click",".commentmodifyreturn",(event)=>{
        const idxValue = $(event.currentTarget).data("idx");
        console.log(idxValue);
        $.ajax({
                    url: '/commentreturn',
                    type: 'POST',
                    contentType: 'application/json',
                    data: JSON.stringify({
                       idx : idxValue
                    }),
                    success: (data) => {
                        $("#" + idxValue).html(`
                            <div id="${data.commentidx.trim()}">
                                <img style="width:20px; height:20px;" src="/iconimg?userid=${data.userid.trim()}" alt="사용자 아이콘">
                                <span>${data.nick.trim()}</span>
                                |
                                <span>${data.comment.trim()}</span>
                                |
                                <span>${data.regdate.trim()}</span>
                                <button class="commentmodify" data-idx="${data.commentidx.trim()}" data-content="${data.comment.trim()}">수정</button>
                                <button class="commentdelete" data-idx="${data.commentidx.trim()}">삭제</button>
                            </div>
                        `);
                    },
                    error: () => {
                        alert('오류가 발생했습니다');
                    }
                });
   })




})
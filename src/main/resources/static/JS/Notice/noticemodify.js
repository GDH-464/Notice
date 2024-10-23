
$(document).ready(()=>{
    $(".announcement").on("change",()=>{
        if($(".announcement").is(":checked"))
        {
            $(".grade").val(1);
        }
        else
        {
            $(".grade").val(2);
        }
    })
    $(".returnbtn").on("click",()=>{
        window.location.href="/notice"
    })
     const validateForm = () => {
        const checks = [
            { check: $(".title").val(), message: "제목을 입력해주세요", field: ".title" },
            { check: $(".content").val(), message: "내용을 입력해주세요", field: ".content" },
        ];
        for (const { check, message, field } of checks) {
            if (check === undefined ||check.length === 0) {
                alert(message);
                $(field).focus();
                return false;
            }
        }
        return true;
    };

    $(".onsubmit").on("submit", (event) => {
        event.preventDefault(); // 기본 제출 방지
        if (validateForm()) {
            const myDropzone = Dropzone.forElement("#fileDropzone");
            const formData = new FormData();

            // NoticeDTO 필드 값 추가
            formData.append("userid", $("input[name='userid']").val());
            formData.append("nick", $("input[name='nick']").val());
            formData.append("grade", $("input[name='grade']").val());
            formData.append("title", $(".title").val());
            formData.append("content", $(".content").val());

            // Dropzone 파일 추가
            if (myDropzone != null) {
                myDropzone.getAcceptedFiles().forEach((file) => {
                    formData.append("ofile", file);
                });
            }

            // AJAX 요청
            $.ajax({
                url: '/noticemodify_proc',
                type: 'POST',
                data: formData,
                contentType: false,
                processData: false,
                success: function (response) {
                    // 성공 시 처리
                    window.location.href = "/notice";
                },
                error: function (error) {
                    // 오류 처리
                }
            });
        }
    });

})


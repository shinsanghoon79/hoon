<!DOCTYPE html>
<html lang="kr">
<head>
    <title>Doubles QnA | QnA Post Update Form</title>
    {{> /include/header}}
</head>
<body>
{{> /include/navigation}}
<div class="container" id="main">
    <div class="col-md-12 col-sm-12 col-lg-10 col-lg-offset-1">
        <div class="panel panel-default content-main">
            {{#question}}
            <form name="question" method="post" action="/questions/{{id}}">
                <input type="hidden" name="_method" value="put">
                <div class="form-group">
                    <label for="title">제목</label>
                    <input type="text" class="form-control" id="title" name="title" value="{{title}}" placeholder="제목"/>
                </div>
                <div class="form-group">
                    <label for="contents">내용</label>
                    <textarea name="contents" id="contents" rows="5" class="form-control">{{contents}}</textarea>
                </div>
                <button type="submit" class="btn btn-success clearfix pull-right">질문 수정</button>
                <div class="clearfix"/>
            </form>
            {{/question}}
        </div>
    </div>
</div>
{{> /include/footer}}
</body>
</html>
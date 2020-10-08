<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="style_meal.css">
    <title>Добавить еду</title>
</head>
<body>
<a href="index.html">На главную</a><br/>
<a href="meals">Вся еда</a><br/>

<h1>Добавить еду</h1>
<form action="edit" method="post">
    <table>
        <tr>
            <th>Дата:</th>
            <td><label>
                <input type="datetime-local" name="date" pattern="dd.MM.yyyy hh:mm:ss" placeholder="Пример: 21.12.2000 18:01:21"/>
            </label></td>
        </tr>
        <tr>
            <th>Описание</th>
            <td>
                <label>
                    <select name="description">
                        <option>Завтрак</option>
                        <option>Обед</option>
                        <option>Ужин</option>
                        <option>Полудник</option>
                        <option>Ланч</option>
                        <option>Был жер</option>
                    </select>
                </label>
            </td>
        </tr>
        <tr>
            <th>Калории:</th>
            <td><label>
                <input type="number" name="calories"/>
            </label></td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="Добавить" /></td>
        </tr>
    </table>
</form>
</body>
</html>

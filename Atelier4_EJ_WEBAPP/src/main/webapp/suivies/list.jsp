<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <title>Liste des Notes (Suivie)</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body { 
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #f0c0e8 0%, #d89bc4 100%);
            min-height: 100vh;
            padding: 20px;
        }
        .container {
            max-width: 1200px;
            margin: 0 auto;
            background: white;
            border-radius: 15px;
            box-shadow: 0 10px 40px rgba(0,0,0,0.2);
            padding: 30px;
        }
        h1 { 
            color: #333;
            margin-bottom: 25px;
            font-size: 2em;
            text-align: center;
            background: linear-gradient(135deg, #e8c4d0 0%, #d8a7c9 100%);
            -webkit-background-clip: text;
            -webkit-text-fill-color: transparent;
            background-clip: text;
        }
        .message {
            background: #f0e6f4;
            color: #5a3d5c;
            padding: 12px 20px;
            border-radius: 8px;
            margin-bottom: 20px;
            border-left: 4px solid #e8c4d0;
        }
        .toolbar {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 25px;
            flex-wrap: wrap;
            gap: 15px;
        }
        .btn { 
            padding: 10px 20px;
            text-decoration: none;
            color: white;
            border-radius: 8px;
            font-weight: 600;
            transition: all 0.3s ease;
            display: inline-block;
            box-shadow: 0 4px 15px rgba(0,0,0,0.2);
        }
        .btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 20px rgba(0,0,0,0.3);
        }
        .btn-add { 
            background: linear-gradient(135deg, #e8c4d0 0%, #d8a7c9 100%);
        }
        .btn-edit { 
            background: linear-gradient(135deg, #f8d7da 0%, #e8c4d0 100%);
            color: #8b4789;
            padding: 6px 12px;
            font-size: 0.85em;
            margin-right: 5px;
        }
        .btn-delete { 
            background: linear-gradient(135deg, #f0c0e8 0%, #d89bc4 100%);
            padding: 6px 12px;
            font-size: 0.85em;
        }
        .btn-home {
            background: linear-gradient(135deg, #f8d7da 0%, #e8c4d0 100%);
            color: #8b4789;
        }
        table { 
            width: 100%;
            border-collapse: separate;
            border-spacing: 0;
            overflow: hidden;
            border-radius: 10px;
            box-shadow: 0 0 20px rgba(0,0,0,0.1);
        }
        th, td { 
            padding: 15px;
            text-align: left;
            border-bottom: 1px solid #f0f0f0;
        }
        th { 
            background: linear-gradient(135deg, #e8c4d0 0%, #d8a7c9 100%);
            color: white;
            font-weight: 600;
            text-transform: uppercase;
            font-size: 0.85em;
            letter-spacing: 0.5px;
        }
        tbody tr {
            transition: all 0.3s ease;
        }
        tbody tr:hover { 
            background-color: #fef7fc;
            transform: scale(1.01);
        }
        tbody tr:last-child td {
            border-bottom: none;
        }
        .note-badge {
            background: linear-gradient(135deg, #c8a2c8 0%, #b88ab8 100%);
            color: white;
            padding: 5px 12px;
            border-radius: 20px;
            font-weight: bold;
            font-size: 1.1em;
        }
        .note-badge.excellent { background: linear-gradient(135deg, #e8c4d0 0%, #d8a7c9 100%); }
        .note-badge.good { background: linear-gradient(135deg, #f0c0e8 0%, #d89bc4 100%); }
        .note-badge.average { background: linear-gradient(135deg, #f5b8d4 0%, #e5a4c4 100%); }
        .note-badge.poor { background: linear-gradient(135deg, #f5a4c4 0%, #e590b8 100%); }
        .empty-state {
            text-align: center;
            padding: 40px;
            color: #999;
            font-style: italic;
        }
        @media (max-width: 768px) {
            table { font-size: 0.85em; }
            th, td { padding: 10px 5px; }
            h1 { font-size: 1.5em; }
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>📊 Liste des Notes</h1>
        
        <% 
            List<?> suivies = (List<?>) request.getAttribute("suivies");
            String message = (String) request.getAttribute("message");
            
            if (message != null) {
                out.println("<div class='message'>" + message + "</div>");
            }
        %>
        
        <div class="toolbar">
            <a href="${pageContext.request.contextPath}/suivie?action=add" class="btn btn-add">➕ Ajouter une note</a>
            <a href="${pageContext.request.contextPath}/" class="btn btn-home">🏠 Retour à l'accueil</a>
        </div>
        
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Étudiant</th>
                    <th>Module</th>
                    <th>Note</th>
                    <th>Date</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <% 
                    if (suivies != null && !suivies.isEmpty()) {
                        for (Object obj : suivies) {
                            Object id = obj.getClass().getMethod("getId").invoke(obj);
                            Object note = obj.getClass().getMethod("getNote").invoke(obj);
                            Object date = obj.getClass().getMethod("getDate").invoke(obj);
                            
                            Object etudiant = obj.getClass().getMethod("getEtudiant").invoke(obj);
                            String etudiantNom = etudiant != null ? 
                                (String) etudiant.getClass().getMethod("getNom").invoke(etudiant) + " " +
                                (String) etudiant.getClass().getMethod("getPrenom").invoke(etudiant) : "N/A";
                            
                            Object module = obj.getClass().getMethod("getModule").invoke(obj);
                            String moduleNom = module != null ? 
                                (String) module.getClass().getMethod("getNom").invoke(module) : "N/A";
                            
                            // Determine note class
                            double noteVal = note != null ? (Double) note : 0.0;
                            String noteClass = noteVal >= 16 ? "excellent" : 
                                             noteVal >= 14 ? "good" : 
                                             noteVal >= 10 ? "average" : "poor";
                %>
                <tr>
                    <td><strong>#<%= id %></strong></td>
                    <td>👤 <%= etudiantNom %></td>
                    <td>📚 <%= moduleNom %></td>
                    <td><span class="note-badge <%= noteClass %>"><%= note %>/20</span></td>
                    <td>📅 <%= date %></td>
                    <td style="white-space: nowrap;">
                        <a href="${pageContext.request.contextPath}/suivie?action=edit&id=<%= id %>" class="btn btn-edit">✏️ Modifier</a>
                        <a href="${pageContext.request.contextPath}/suivie?action=delete&id=<%= id %>" 
                           onclick="return confirm('Êtes-vous sûr de vouloir supprimer cette note?');" 
                           class="btn btn-delete">🗑️ Supprimer</a>
                    </td>
                </tr>
                <% 
                        }
                    } else {
                %>
                <tr>
                    <td colspan="6" class="empty-state">📭 Aucune note trouvée</td>
                </tr>
                <% } %>
            </tbody>
        </table>
    </div>
</body>
</html>

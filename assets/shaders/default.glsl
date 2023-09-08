#type vertex
#version 330 core
layout (location=0) in vec3 a_Pos;
layout (location=1) in vec4 a_Color;
layout (location=2) in vec2 a_TexCoords;
layout (location=3) in float a_TexId;

uniform mat4 u_Projection;
uniform mat4 u_View;

out vec4 f_Color;
out vec2 f_TexCoords;
out float f_TexId;

void main()
{
    f_Color = a_Color;
    f_TexCoords = a_TexCoords;
    f_TexId = a_TexId;

    gl_Position = u_Projection * u_View * vec4(a_Pos, 1.0f);
}

#type fragment
#version 330 core

in vec4 f_Color;
in vec2 f_TexCoords;
in float f_TexId;

uniform sampler2D u_Textures[8];

out vec4 color;

void main()
{
    if (f_TexId > 0) {
        int id = int(f_TexId);
        color = f_Color * texture(u_Textures[id], f_TexCoords);
    } else {
        color = f_Color;
    }
}

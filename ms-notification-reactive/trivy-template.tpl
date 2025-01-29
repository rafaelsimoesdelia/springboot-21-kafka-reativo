{{ range . }}
{{ range .Vulnerabilities }}
ID: {{ .VulnerabilityID }}
Description: {{ .Description }}
Fixed Version: {{ .FixedVersion }}
{{ end }}
{{ end }}
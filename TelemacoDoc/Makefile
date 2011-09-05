all: clean
	dia -t png *.dia
	./../TelemacoServer/src/manage.py graph_models telemaco -g -o er-diagram.png

clean:
	$(RM) -rfv *.png
